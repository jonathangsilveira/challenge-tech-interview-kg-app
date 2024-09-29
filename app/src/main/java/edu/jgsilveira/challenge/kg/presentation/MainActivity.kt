package edu.jgsilveira.challenge.kg.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import edu.jgsilveira.challenge.kg.R
import edu.jgsilveira.challenge.kg.databinding.ActivityMainBinding
import edu.jgsilveira.challenge.kg.presentation.activesports.ActiveSportEventsAction
import edu.jgsilveira.challenge.kg.presentation.activesports.ActiveSportEventsUIError
import edu.jgsilveira.challenge.kg.presentation.activesports.ActiveSportEventsState
import edu.jgsilveira.challenge.kg.presentation.activesports.ActiveSportEventsViewModel
import edu.jgsilveira.challenge.kg.presentation.view.sporteventsection.SportEventAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<ActiveSportEventsViewModel>()
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val sportEventAdapter by lazy {
        SportEventAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshSportEvents()
    }

    private fun setupView() {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.activeSportEventsContainer.adapter = sportEventAdapter
        with(sportEventAdapter) {
            onFilterCheck = viewModel::onSportEventFilterChanged
            onFavoriteCheck = viewModel::onActiveSportEventFavoriteChanged
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect(::handleState)
            }
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.action.collect(::handleAction)
            }
        }
    }

    private fun handleState(state: ActiveSportEventsState) {
        when {
            state.isLoading -> onLoadingState()

            state.error != null -> onErrorState(state.error)

            else -> onSuccessState(state)
        }
    }

    private fun handleAction(action: ActiveSportEventsAction) {
        when (action) {
            is ActiveSportEventsAction.ShowErrorSnackBar ->
                showSnackBar(getString(action.resId))
        }
    }

    private fun onSuccessState(state: ActiveSportEventsState) {
        binding.activeSportEventsContainer.isVisible = true
        binding.activeSportEventsProgress.isVisible = false
        binding.activeSportEventsError.isVisible = false
        sportEventAdapter.submitList(state.results)
    }

    private fun onErrorState(error: ActiveSportEventsUIError) {
        binding.activeSportEventsContainer.isVisible = false
        binding.activeSportEventsProgress.isVisible = false
        with(binding.activeSportEventsError) {
            isVisible = true
            setTitle(
                title = getString(error.messageResId)
            )
            setPrimaryButtonText(
                text = getString(R.string.reload)
            )
            setOnPrimaryButtonClickListener {
                viewModel.refreshSportEvents()
            }
        }
    }

    private fun onLoadingState() {
        binding.activeSportEventsContainer.isVisible = true
        binding.activeSportEventsProgress.isVisible = true
        binding.activeSportEventsError.isVisible = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
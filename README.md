# Overview

This Android project displays a list of active events grouped by sport as a challenge to a 
tech interview for software engineer role.

# Requirements



# Configuration

- Min. Android SDK: 21.
- Target Android SDK: 34.

# Project structure

A single module (app) and logically layered by data, domain and 
presentation packages.

# Architectural Pattern

This project follows the Model-View-ViewModel (MVVM) presentation architectural pattern, which provides a clear separation of concerns and promotes testability and maintainability.

# Key Components:

## Model:

- Represents the data and business logic of the application.
- Includes data classes, repositories, and use cases.
- Responsible for fetching, storing, and manipulating data.

## View:

- Represents the UI layer of the application.
- Includes Activities, Fragments, and custom Views.
- Responsible for displaying data to the user and handling user interactions.

## ViewModel:

- Acts as an intermediary between the View and the Model.
- Prepares and transforms data from the Model into a format suitable for display in the View.
- Handles user interactions and triggers actions in the Model.
- Exposes state to the View using StateFlow.

## Benefits of MVVM:

- Improved Testability: Each component can be tested independently.
- Enhanced Maintainability: Changes to one component have minimal impact on others.
- Increased Code Reusability: ViewModels can be reused across multiple Views.

# Additional Architectural Considerations:

- Dependency Injection (Koin): Used to manage dependencies and improve testability.
- Coroutines: Used for asynchronous operations and background tasks.
- Retrofit: Used for make remote calls to mocked API.
- KotlinX Serialization: Deserialization of JSON responses.
- Repository Pattern: Provides an abstraction layer for data access.
- Clean Architecture Principles: Applied to further enhance separation of concerns and maintainability.

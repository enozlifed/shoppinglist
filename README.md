## Architecture Overview

This project demonstrates a clean, modular architecture based on the MVVM pattern, with a strong focus on separation of concerns and testability.

### Application Functionality

The app is a **simple shopping list** that allows users to:

- **Add new items** to the list by pressing the **Floating Action Button (FAB)**.  
  This opens a **dialog** where the user can enter the item they wish to add. Once confirmed, the item is saved and displayed in the list.
- **Delete items** via swipe gestures.
- **Mark items as completed** by checking/unchecking a checkbox in the list.
- All changes are **persisted in a local Room database**, ensuring that the state is retained across sessions.

The RecyclerView dynamically reflects changes to the data, and updates are efficiently handled using `DiffUtil`.

### Project Structure

- **UI Layer** (`ui/`):  
  Contains all presentation logic, including Fragments, ViewModels, Adapters, and ViewHolders.
- **Data Layer** (`data/`):  
  Responsible for data access and management. Implements the Repository pattern with Room (via DAO and Database classes).
- **Dependency Injection Layer** (`di/`):  
  Uses Koin for lightweight dependency injection, managing the creation and lifecycle of components such as repositories, DAOs, and view models.
- **Utility Layer** (`util/`):  
  Contains reusable utility classes for functionality such as list comparison (`DiffUtil`) and swipe gestures.

### Architectural Principles

- **Separation of Concerns (SoC)**:  
  Each layer has a single responsibility, making the codebase easier to understand and maintain.
- **MVVM Architecture**:  
  ViewModels expose state to the UI and handle business logic. Fragments remain focused on UI rendering and user interaction.
- **Repository Pattern**:  
  Abstracts the data source and provides a clean API to the rest of the app.
- **Dependency Injection with Koin**:  
  Promotes loose coupling and simplifies testing by decoupling component creation and configuration.
- **Modular, Reusable Components**:  
  UI components and utilities are designed for reusability and maintainability.

### Objective

The goal of this architectural setup is to ensure maintainability, scalability, and testability by enforcing clear boundaries between the UI, business logic, and data layers.

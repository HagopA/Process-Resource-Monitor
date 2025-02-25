# Process Resource Monitor

A JavaFX application for monitoring system processes, their CPU usage, and memory consumption on Windows systems.

## Features

- View all running processes with their PIDs
- Add processes to monitor by PID
- Remove processes from monitoring
- Real-time display of CPU and memory usage for monitored processes
- User-friendly GUI with alert boxes for feedback
- Automatic refresh of process statistics

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- JavaFX SDK (included in JDK 8, separate for JDK 11+)
- Windows operating system (uses Windows-specific commands like `tasklist` and `wmic`)

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/HagopA/Process-Resource-Monitor.git
   ```

2. Navigate to the project directory:
   ```bash
   cd Process-Resource-Monitor
   ```

3. Compile and run the application:
   - For JDK 8:
     ```bash
     javac *.java
     java ProcessMonitorApp
     ```
   - For JDK 11+ with separate JavaFX:
     ```bash
     javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls *.java
     java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls ProcessMonitorApp
     ```

## Usage

1. Launch the application to see the main window with five options:
   - "View running processes" - Displays all currently running processes
   - "Select Process to Start Monitoring" - Add a process to monitor by entering its PID
   - "Select Process to Stop Monitoring" - Remove a process from monitoring
   - "Display CPU and Memory Usage of Selected Processes" - Shows real-time metrics
   - "Exit" - Closes the application

2. Use the interface:
   - Enter PIDs numerically in the input fields
   - Use the "Back" button to return to the main menu
   - Scroll through process lists when viewing running processes
   - Receive success/error messages via alert boxes

## Project Structure

- `ProcessMonitorApp.java` - Main application class with UI setup and event handling
- `ProcessOperations.java` - Handles process data collection and management
- `ProcessInfo.java` - Data model for process information
- `AlertBox.java` - Utility class for displaying alert messages

## Technical Details

- Built with JavaFX for the graphical interface
- Uses Windows `tasklist` and `wmic` commands to gather process information
- Implements real-time monitoring with a 1-second refresh rate
- Thread-safe process monitoring using synchronization
- Error handling for invalid PIDs and system command execution

## Limitations

- Windows-only due to reliance on `tasklist` and `wmic` commands
- Requires administrator privileges for some process information
- No persistent storage of monitored processes between sessions

## Acknowledgments

- Built with JavaFX
- Created as a process monitoring demonstration

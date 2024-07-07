# SterileTrack 

SterileTrack is an Android application designed to monitor and improve hand hygiene practices among medical staff in ICU settings. The app utilizes Firebase for real-time data storage and provides a streamlined interface for data entry and monitoring.

## Purpose

The primary goal of SterileTrack is to enhance hand hygiene compliance among medical professionals working in Intensive Care Units (ICUs). By tracking and analyzing hand hygiene practices, the app aims to reduce hospital-acquired infections and improve overall patient safety.

## Key Features

- **User Authentication:** Secure login system for authorized access.
- **ICU Selection:** Allows selection of different ICUs for observations.
- **Designation Selection:** Enables users to specify their role (e.g., doctor, nurse) during data entry.
- **Observation Entry:** Structured questionnaire for collecting hand hygiene practice data.
- **Firebase Integration:** Utilizes Firebase Realtime Database for storing observational data.
- **Data Submission and Validation:** Ensures completeness and accuracy of submitted data.
- **User Feedback:** Provides immediate feedback on data submission status.
- **Error Handling:** Alerts users to incomplete form submissions for corrective action.

## Technical Details

- **Language:** Developed in Kotlin, leveraging Android SDK.
- **Database:** Firebase Realtime Database for storing and retrieving data.
- **User Interface:** XML layouts for intuitive UI components.
- **Activities:** Implements Android activities for managing user interactions.

## Installation

To use SterileTrack, follow these steps:

1. Clone the repository:
   ```
   git clone https://github.com/your-username/SterileTrack.git
   ```
2. Open the project in Android Studio.
3. Connect the project to your Firebase project by adding your `google-services.json` file.

## Usage

1. Launch the app on an Android device or emulator.
2. Log in with your credentials.
3. Select the ICU and specify your role.
4. Enter observations using the provided questionnaire.
5. Submit data to Firebase for real-time tracking and monitoring.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your suggested improvements.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Built with [Firebase](https://firebase.google.com/) Realtime Database.
- UI Design inspired by Material Design guidelines.



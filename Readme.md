# Spotify Clone

This is a Java-based clone of the Spotify Desktop App, built using Java Swing for the GUI and SQLite for the database. The application allows users to manage and play music, including features for user signup and login, viewing all songs, adding and removing songs, and searching for specific songs.
Created By Chandan Kumar Under Programmers Tech

## Features

- User Signup and Login
- View All Songs
- Add New Songs
- Remove Existing Songs
- Search for Songs
- Play Songs (supports MP3 and WAV formats)

## Requirements

- Java 8 or higher
- JLayer library for MP3 playback
- SQLite database
- MySQL database for user data (ensure the credentials in the code match your MySQL setup)

## Setup

1. **Clone the Repository**

    ```bash
    git clone https://github.com/yourusername/spotify-clone.git
    cd spotify-clone
    ```

2. **Configure the Database**

    - Set up an SQLite database or a MySQL database based on your preference.
    - Update the database connection details in the `SpotifyGUI` class constructor:
      ```java
      ```

3. **Add Dependencies**

   Ensure you have the required libraries in your project:
    - JLayer library for MP3 playback.
    - JDBC drivers for MySQL.

4. **Run the Application**

   Compile and run the application using your preferred IDE or build tool. For example, using IntelliJ IDEA:
    - Open the project.
    - Build and run `SpotifyGUI`.

## Usage

- **Signup**: Click on "Signup" to create a new user account.
- **Login**: Click on "Login" to access the application with your credentials.
- **All Songs**: View the list of all available songs.
- **Add Song**: Add a new song to the database.
- **Remove Song**: Remove a song from the database.
- **Search Song**: Search for a specific song in the database.
- **Play Song**: Play a song (MP3 or WAV format).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

- JLayer library for MP3 playback.
- Java Swing for the GUI framework.
- SQLite and MySQL for database management.

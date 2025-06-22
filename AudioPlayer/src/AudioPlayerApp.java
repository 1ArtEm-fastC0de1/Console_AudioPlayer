import java.util.Scanner;

public class AudioPlayerApp {
    private final Scanner scanner = new Scanner(System.in);
    private final MusicLibrary library = new MusicLibrary();
    private final AudioPlayer player = new AudioPlayer();

    public void run() {
        while (true) {
            System.out.println("1. Choose Folder\n2. Exit");
            String input = scanner.nextLine().trim();

            if (input.equals("2")) {
                System.out.println("Goodbye!");
                break;
            }

            if (input.equals("1")) {
                System.out.print("Enter path: ");
                String path = scanner.nextLine().trim();

                if (library.loadFolder(path)) {
                    while (true) {
                        library.listTracks();
                        System.out.print("Choose track number (or 0 to go back): ");
                        String choice = scanner.nextLine().trim();

                        if (choice.equals("0")) break;

                        try {
                            int trackNumber = Integer.parseInt(choice);
                            player.load(library.getTrack(trackNumber));
                            player.playLoop();
                            listenToCommands();  // Блокирующий метод, ждёт stop
                        } catch (Exception e) {
                            System.out.println("ERROR PLAYING FILE: " + e.getMessage());
                        }
                    }
                } else {
                    System.out.println("Folder is empty or does not exist.");
                }
            } else {
                System.out.println("Invalid input. Try again.");
            }
        }
    }

    private void listenToCommands() {
        System.out.println("MUSIC PLAYING...\n" +
                "Enter \"pause\" to pause\n" +
                "Enter \"resume\" to resume\n" +
                "Enter \"stop\" to stop");

        final boolean[] stopped = {false};

        Thread commandThread = new Thread(() -> {
            while (!stopped[0]) {
                String command = scanner.nextLine().trim().toLowerCase();
                switch (command) {
                    case "pause" -> {
                        player.pause();
                        System.out.println("Paused");
                    }
                    case "resume" -> {
                        player.resume();
                        System.out.println("Resumed");
                    }
                    case "stop" -> {
                        player.stop();
                        System.out.println("Stopped");
                        stopped[0] = true;
                    }
                    default -> System.out.println("Invalid command");
                }
            }
        });

        commandThread.start();

        // Блокируем основной поток, пока не будет команда stop
        while (!stopped[0]) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
        }
    }
}

import java.io.File;

public class MusicLibrary {
    private File[] files;

    public boolean loadFolder(String path) {
        File folder = new File(path);
        if (folder.exists() && folder.isDirectory()) {
            files = folder.listFiles();
            return files != null && files.length > 0;
        }
        return false;
    }

    public void listTracks() {
        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ". " + files[i].getName());
        }
    }

    public File getTrack(int index) {
        return files[index - 1];
    }
}

package cz.m0bY_czE.dragons.Handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import cz.m0bY_czE.dragons.Main;

public class ResetWorld {
    public ResetWorld() {
    }

    public static void logSevere(String message) {
        Main.instance.getLogger().severe(message);
    }

    public static void logInfo(String message) {
        Main.instance.getLogger().info(message);
    }

    public void logWarning(String message) {
        Main.instance.getLogger().warning(message);
    }

    public static void importWorlds() {
        boolean errors = false;
        File backupDir = new File(Main.instance.getDataFolder(), "backups");
        File[] var5;
        int var4 = (var5 = backupDir.listFiles()).length;

        for(int var3 = 0; var3 < var4; ++var3) {
            File source = var5[var3];
            if(source.isDirectory()) {
                File target = new File(Main.instance.getServer().getWorldContainer(), source.getName());
                if(target.exists() && target.isDirectory() && !delete(target)) {
                    logSevere("Failed to reset world \"" + source.getName() + "\" - could not delete old " + "world folder.");
                    errors = true;
                } else {
                    try {
                        copyDir(source, target);
                    } catch (IOException var8) {
                        var8.printStackTrace();
                        logSevere("Failed to reset world \"" + source.getName() + "\" - could not import the " + "world from backup.");
                        errors = true;
                    }

                    logInfo("Import of world \"" + source.getName() + "\" " + (errors?"failed!":"succeeded!"));
                    errors = false;
                }
            }
        }

    }

    public void deleteWorlds() {
        boolean worldsListed = false;
        Iterator var3 = Main.instance.getConfig().getStringList("Random-seed.worlds").iterator();

        while(var3.hasNext()) {
            String worldName = (String)var3.next();
            if(!worldsListed) {
                worldsListed = true;
            }

            File target = new File(Main.instance.getServer().getWorldContainer(), worldName);
            if(!target.exists()) {
                logSevere("Could not load world \"" + worldName + "\" with a random seed: no such world " + "exists in the server directory!");
                return;
            }

            if(target.isDirectory()) {
                if(!delete(target)) {
                    logSevere("Failed to delete world \"" + worldName + "\", perhaps the folder is locked?");
                } else {
                    logInfo("Successfully loaded a random seed for world \"" + worldName + "\"!");
                }
            }
        }

        if(!worldsListed) {
            this.logWarning("The random seed option is enabled but no worlds are listed to be deleted and regenerated with random seeds.");
        }

    }

    private static boolean delete(File file) {
        if(file.isDirectory()) {
            File[] var4;
            int var3 = (var4 = file.listFiles()).length;

            for(int var2 = 0; var2 < var3; ++var2) {
                File subfile = var4[var2];
                if(!delete(subfile)) {
                    return false;
                }
            }
        }

        return file.delete();
    }

    private static void copyDir(File source, File target) throws IOException {
        int length;
        if(source.isDirectory()) {
            if(!target.exists()) {
                target.mkdir();
            }

            String[] in = source.list();
            String[] var6 = in;
            length = in.length;

            for(int buffer = 0; buffer < length; ++buffer) {
                String out = var6[buffer];
                File srcFile = new File(source, out);
                File destFile = new File(target, out);
                copyDir(srcFile, destFile);
            }
        } else {
            FileInputStream var9 = new FileInputStream(source);
            FileOutputStream var10 = new FileOutputStream(target);
            byte[] var11 = new byte[1024];

            while((length = var9.read(var11)) > 0) {
                var10.write(var11, 0, length);
            }

            var9.close();
            var10.close();
        }

    }
}


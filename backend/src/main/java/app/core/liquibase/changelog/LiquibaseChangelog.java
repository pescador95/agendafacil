package app.core.liquibase.changelog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class LiquibaseChangelog {

    public void addIncludeAllEntry(File changelogFile, String migrationPath) throws IOException {

        try {

            String indentationIncludeAllLevel = identLevel(7);
            String indentationpath = identLevel(16);

            String includeAllEntry = indentationIncludeAllLevel + "- includeAll:\n" +
                    indentationpath + "path: " + migrationPath;

            List<String> lines = Files.readAllLines(changelogFile.toPath());

            lines.add(includeAllEntry);

            Files.write(changelogFile.toPath(), lines);
        } catch (IOException e) {
            System.err.println("Erro ao atualizar o changelog: " + e.getMessage());
        }
    }

    public String identLevel(int indentationLevel) {
        return " ".repeat(Math.max(0, indentationLevel));
    }
}

package org.app.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.app.model.Job;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@ApplicationScoped
public class Handler {

    private final ObjectMapper mapper;
    private final HashMap<String, Consumer<InputStreamReader>> handler;

    public Handler() {
        handler = new HashMap<>();
        mapper = new ObjectMapper();
        handler.put("./unified_jobs_table.csv", this::handleUnifiedJobsCsv);
    }

    public void handle(InputStream gzippedFile) {
        BufferedReader br = null;

        try (GzipCompressorInputStream gzippedStream = new GzipCompressorInputStream(gzippedFile)) {
            try (TarArchiveInputStream tarInput = new TarArchiveInputStream(gzippedStream)) {
                TarArchiveEntry currentEntry;
                try {
                    currentEntry = tarInput.getNextTarEntry();
                    while (currentEntry != null) {
                        handler.getOrDefault(currentEntry.getName(), this::handleDefault).accept(new InputStreamReader(tarInput));
                        currentEntry = tarInput.getNextTarEntry();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleDefault(InputStreamReader file) {
        Log.info("This file parsing is not implemented yet!");
    }

    private void handleUnifiedJobsCsv(InputStreamReader file) {
        List<String> stringList = handleCsv(file);
        //Remove CSV header
        stringList.remove(0);
        Log.info("Found a unified_table_jobs.csv...Inserting data...");
        stringList.stream().map(Job::new).filter(Job::isPersistable).forEach(x -> x.persist());
    }

    private Map handleJson(InputStream file) {
        try {
            return mapper.readValue(file, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private List<String> handleCsv(InputStreamReader file) {
        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(file);
        String line = null;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            lines.add(line);
        }
        return lines;
    }


}

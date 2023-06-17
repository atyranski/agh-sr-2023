package edu.agh.atyranski.example;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
class StreamWriter extends Thread {
    OutputStream outputStream;

    InputStream inputStream;

    public StreamWriter(
            InputStream inputStream,
            OutputStream outputStream
    ) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        start();
    }

    public void run() {
        byte[] bytes = new byte[80];
        int reasonCode;
        try {
            while ((reasonCode = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, reasonCode);
            }
        } catch (IOException e) {
            log.warn("Unable to run XYZ");
        }

    }
}

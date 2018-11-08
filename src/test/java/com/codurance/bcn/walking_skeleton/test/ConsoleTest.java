package com.codurance.bcn.walking_skeleton.test;

import com.codurance.bcn.walking_skeleton.Console;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.assertThat;

class ConsoleTest {
    private InputStream standardInput;
    private PrintStream standardOutput;
    private ByteArrayOutputStream byteArrayOutputStream;

    @BeforeEach
    void setUp() {
        standardInput = System.in;
        standardOutput = System.out;
        byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));
    }

    @Test
    void echo() throws IOException {
        String[] commands = new String[]{"a","b","c","d","e","f"};
        inputCommands(commands);

        new Console().read();

        assertThat(getLineSeparatedOutput()).isEqualTo(commands);
    }

    private void inputCommands(String... commands) {
        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator());
        for (String command: commands) stringJoiner.add(command);
        stringJoiner.add("");

        String input = stringJoiner.toString();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
    }

    private String[] getLineSeparatedOutput() {
        String outputWithLineSeparators = byteArrayOutputStream.toString();
        if(outputWithLineSeparators.isEmpty()) return new String[]{};
        return outputWithLineSeparators.split(System.lineSeparator());
    }

    @AfterEach
    void tearDown() {
        System.setIn(standardInput);

        System.out.flush();
        System.setOut(standardOutput);
    }
}
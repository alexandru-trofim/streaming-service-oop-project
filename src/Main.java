import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Input;
import lombok.Getter;
import runProgram.Program;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    static int count = 1;
    public static void main(String[] args) throws IOException {
        Program program = new Program();

        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(args[0]),
                Input.class);
        ArrayNode output = objectMapper.createArrayNode();


        program.run(inputData, output);


        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);

        if (count == 10) {
            File gen_out = new File(args[1]);
            File out = new File(args[1] + count);

            Files.copy(gen_out.toPath(), out.toPath());
        }
        count++;
    }
}

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Input;
import run.program.Program;

import java.io.File;
import java.io.IOException;

public class Main {
    /**
     * The main function that gets an input  file
     * and an output file. It runs the program
     * and writes the result to the  output file.
     * @param args first argument is the input file
     *             and the second is the output file
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {
        Program program = new Program();

        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(args[0]),
                Input.class);
        ArrayNode output = objectMapper.createArrayNode();


        program.run(inputData, output);


        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);

    }
}

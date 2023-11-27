/*
 * Copyright (C) 2023 University of Pittsburgh.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package edu.pitt.dbmi.fhir.converter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 *
 * Nov 26, 2023 3:12:27 AM
 *
 * @author Kevin V. Bui (kvb2univpitt@gmail.com)
 */
public class FhirConverterApplicationTest {

    @TempDir
    public static Path tempDir;

    /**
     * Test of main method, of class FhirConverterApplication.
     */
    @Test
    public void testMain() throws IOException {
        Path file = new File(getClass().getResource("/data/brainai/patients.tsv").getFile()).toPath();
        String dirOut = createSubDir(tempDir, "fhir_converter").toString();
        String[] args = {
            "--data", file.toString(),
            "--type", "patient",
            "--format", "ndjson",
            "--dir-out", dirOut
        };
        FhirConverterApplication.main(args);
    }

    private Path createSubDir(Path dir, String name) throws IOException {
        return Files.createDirectory(Paths.get(dir.toString(), name));
    }

}

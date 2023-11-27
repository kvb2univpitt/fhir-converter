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

import edu.pitt.dbmi.fhir.resource.mapper.r4.converter.ResourceFormat;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

/**
 *
 * Nov 26, 2023 3:33:49 AM
 *
 * @author Kevin V. Bui (kvb2univpitt@gmail.com)
 */
public final class CmdParser {

    private CmdParser() {
    }

    public static CmdArgs parse(String[] args) throws ParseException {
        CommandLine cmd = (new DefaultParser()).parse(CmdOptions.getOptions(), args);
        Path healthDataFile = Paths.get(cmd.getOptionValue(CmdOptions.DATA));
        Datatype datatype = getDatatype(cmd.getOptionValue(CmdOptions.TYPE));
        ResourceFormat resourceFormat = getResourceFormat(cmd.getOptionValue(CmdOptions.RESOURCE_FORMAT));
        Path dirOut = getDirOut(cmd.getOptionValue(CmdOptions.DIR_OUT));

        return new CmdArgs(healthDataFile, datatype, resourceFormat, dirOut);
    }

    private static Path getDirOut(String dirOut) throws ParseException {
        if (dirOut == null) {
            return null;
        } else {
            Path outputDir = Paths.get(dirOut);
            if (Files.exists(outputDir)) {
                if (Files.isDirectory(outputDir)) {
                    return outputDir;
                } else {
                    throw new ParseException(String.format("Is not a directory: %s", dirOut));
                }
            } else {
                throw new ParseException(String.format("Directory does not exist: %s", dirOut));
            }
        }
    }

    private static ResourceFormat getResourceFormat(String format) throws ParseException {
        switch (format.toLowerCase()) {
            case "json" -> {
                return ResourceFormat.JSON;
            }
            case "ndjson" -> {
                return ResourceFormat.NDJSON;
            }
            case "xml" -> {
                return ResourceFormat.XML;
            }
            case "rdf" -> {
                return ResourceFormat.RDF;
            }
            default ->
                throw new ParseException(String.format("Unknown resource format '%s'.", format));
        }
    }

    private static Datatype getDatatype(String type) throws ParseException {
        switch (type.toLowerCase()) {
            case "patient" -> {
                return Datatype.PATIENT;
            }
            case "encounter" -> {
                return Datatype.ENCOUNTER;
            }
            case "observation" -> {
                return Datatype.OBSERVATION;
            }
            default ->
                throw new ParseException(String.format("Unknown datatype '%s'.", type));
        }
    }

    public static void showHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(-1);
        formatter.printHelp(getHelpTitle(), CmdOptions.getOptions(), true);
    }

    private static String getHelpTitle() {
        Package appPackage = FhirConverterApplication.class.getPackage();
        String title = appPackage.getImplementationTitle();
        String version = appPackage.getImplementationVersion();

        return (title == null || version == null)
                ? "java -jar fhir-converter.jar"
                : String.format("java -jar %s-%s.jar", title, version);
    }

}

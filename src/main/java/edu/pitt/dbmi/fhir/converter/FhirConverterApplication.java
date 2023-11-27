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

import edu.pitt.dbmi.fhir.resource.mapper.r4.converter.FhirResourceConvertException;
import org.apache.commons.cli.ParseException;

/**
 * This command-line application converts health data in tab-separated values
 * (TSV) to FHIR format.
 *
 * Nov 26, 2023 2:41:19 AM
 *
 * @author Kevin V. Bui (kvb2univpitt@gmail.com)
 */
public class FhirConverterApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CmdArgs cmdArgs = getCmdArgs(args);
        if (cmdArgs == null) {
            CmdParser.showHelp();
            System.exit(-1);
        }

        try {
            FhirConverter fhirConverter = new FhirConverter(cmdArgs);
            fhirConverter.convert();
        } catch (FhirResourceConvertException exception) {
            exception.printStackTrace(System.err);
        }
    }

    private static CmdArgs getCmdArgs(String[] args) {
        try {
            return CmdParser.parse(args);
        } catch (ParseException exception) {
            System.err.println(exception.getMessage());
            return null;
        }
    }

}

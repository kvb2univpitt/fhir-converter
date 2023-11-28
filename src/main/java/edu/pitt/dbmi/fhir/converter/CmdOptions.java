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

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 *
 * Nov 26, 2023 4:29:25 AM
 *
 * @author Kevin V. Bui (kvb2univpitt@gmail.com)
 */
public final class CmdOptions {

    public static final Option DATA = Option.builder()
            .longOpt("data")
            .desc("Health data.")
            .hasArg(true)
            .required().build();

    public static final Option TYPE = Option.builder()
            .longOpt("type")
            .desc("Type of health data being converted: patient, encounter, observation, medication-administration, location.")
            .hasArg(true)
            .required().build();

    public static final Option RESOURCE_FORMAT = Option.builder()
            .longOpt("format")
            .desc("Output resource format: json, ndjson, xml, rdf.")
            .hasArg(true)
            .required().build();

    public static final Option DIR_OUT = Option.builder()
            .longOpt("dir-out")
            .desc("Directory to write output file to.")
            .hasArg(true)
            .required(false).build();

    private CmdOptions() {
    }

    public static Options getOptions() {
        Options opts = new Options();

        opts.addOption(DATA);
        opts.addOption(TYPE);
        opts.addOption(RESOURCE_FORMAT);
        opts.addOption(DIR_OUT);

        return opts;
    }

}

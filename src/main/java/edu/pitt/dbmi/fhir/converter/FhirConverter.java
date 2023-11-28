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

import ca.uhn.fhir.context.FhirContext;
import static edu.pitt.dbmi.fhir.converter.Datatype.PATIENT;
import edu.pitt.dbmi.fhir.resource.mapper.r4.brainai.EncounterResourceMapper;
import edu.pitt.dbmi.fhir.resource.mapper.r4.brainai.LocationResourceMapper;
import edu.pitt.dbmi.fhir.resource.mapper.r4.brainai.MedicationAdministrationResourceMapper;
import edu.pitt.dbmi.fhir.resource.mapper.r4.brainai.ObservationResourceMapper;
import edu.pitt.dbmi.fhir.resource.mapper.r4.brainai.PatientResourceMapper;
import edu.pitt.dbmi.fhir.resource.mapper.r4.converter.FhirResourceConvertException;
import edu.pitt.dbmi.fhir.resource.mapper.r4.converter.FhirResourceConverter;
import edu.pitt.dbmi.fhir.resource.mapper.util.Delimiters;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.hl7.fhir.r4.model.Resource;

/**
 *
 * Nov 26, 2023 5:12:28 PM
 *
 * @author Kevin V. Bui (kvb2univpitt@gmail.com)
 */
public class FhirConverter {

    private final CmdArgs cmdArgs;

    public FhirConverter(CmdArgs cmdArgs) {
        this.cmdArgs = cmdArgs;
    }

    public void convert() throws FhirResourceConvertException {
        try (BufferedWriter writer = Files.newBufferedWriter(getOutputFile(), StandardOpenOption.CREATE)) {
            FhirResourceConverter fhirConverter = new FhirResourceConverter(FhirContext.forR4());
            fhirConverter.convert(getResources(), cmdArgs.resourceFormat(), writer);
        } catch (IOException exception) {
            throw new FhirResourceConvertException(exception);
        }
    }

    private Path getOutputFile() {
        String fileName = cmdArgs.healthDataFile().getFileName().toString();
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        fileName = String.format("%s.%s", fileName, cmdArgs.resourceFormat().name().toLowerCase());

        return (cmdArgs.dirOut() == null)
                ? Paths.get(fileName)
                : Paths.get(cmdArgs.dirOut().toString(), fileName);
    }

    private List<Resource> getResources() {
        return switch (cmdArgs.datatype()) {
            case PATIENT ->
                mapToPatientResources();
            case ENCOUNTER ->
                mapToEncounterResources();
            case OBSERVATION ->
                mapToObservationResources();
            case MEDICATION_ADMINISTRATION ->
                mapToMedicationAdministrationResources();
            case LOCATION ->
                mapToLocationResources();
            default ->
                Collections.EMPTY_LIST;
        };
    }

    private List<Resource> mapToLocationResources() {
        Path file = cmdArgs.healthDataFile();

        return LocationResourceMapper.getLocations(file, Delimiters.TAB_DELIM).stream()
                .map(e -> (Resource) e)
                .collect(Collectors.toList());
    }

    private List<Resource> mapToMedicationAdministrationResources() {
        Path file = cmdArgs.healthDataFile();

        return MedicationAdministrationResourceMapper.getMedicationAdministrations(file, Delimiters.TAB_DELIM).stream()
                .map(e -> (Resource) e)
                .collect(Collectors.toList());
    }

    private List<Resource> mapToObservationResources() {
        Path file = cmdArgs.healthDataFile();

        return ObservationResourceMapper.getObservations(file, Delimiters.TAB_DELIM).stream()
                .map(e -> (Resource) e)
                .collect(Collectors.toList());
    }

    private List<Resource> mapToEncounterResources() {
        Path file = cmdArgs.healthDataFile();

        return EncounterResourceMapper.getEncounters(file, Delimiters.TAB_DELIM).stream()
                .map(e -> (Resource) e)
                .collect(Collectors.toList());
    }

    private List<Resource> mapToPatientResources() {
        Path file = cmdArgs.healthDataFile();

        return PatientResourceMapper.getPatients(file, Delimiters.TAB_DELIM).stream()
                .map(e -> (Resource) e)
                .collect(Collectors.toList());
    }

}

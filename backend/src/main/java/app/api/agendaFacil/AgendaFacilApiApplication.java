package app.api.agendaFacil;


import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

import static app.core.helpers.utils.Info.logProjectInfo;

@QuarkusMain
public class AgendaFacilApiApplication {

    public static void main(String... args) {
        Quarkus.run(args);
        logProjectInfo(false);
    }
}
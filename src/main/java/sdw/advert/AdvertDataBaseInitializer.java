package sdw.advert;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Slf4j
@RequiredArgsConstructor
@Component
@ConfigurationProperties("data")
public class AdvertDataBaseInitializer {

    private final static String DATA_FILE_NAME = "src/main/resources/data.csv";
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");

    private final AdvertRepository advertRepository;

    @PostConstruct
    void postConstruct() throws IOException {
        initialize();
    }

    public void initialize() throws IOException {
        try {
            log.info("START IMPORT DATA FROM CSV");
            int i = 1;
            try (Reader reader = Files.newBufferedReader(Paths.get(DATA_FILE_NAME));
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
                for (CSVRecord csvRecord : csvParser) {
                    Advert advert = new Advert(csvRecord.get("Datasource"),
                            csvRecord.get("Campaign"),
                            LocalDate.parse(csvRecord.get("Daily"), DATE_TIME_FORMATTER),
                            Integer.parseInt(csvRecord.get("Clicks")),
                            Integer.parseInt(csvRecord.get("Impressions"))
                    );
                    advertRepository.save(advert);
                    i++;
                }
            }
            log.info("FINISH IMPORT DATA FROM CSV, IMPORTED ROWS: {} ", i);
        }catch(Exception e){
            log.error("",e);
            throw e;
        }
    }

}

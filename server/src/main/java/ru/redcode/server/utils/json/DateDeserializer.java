package ru.redcode.server.utils.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.redcode.server.exception.ServerException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static ru.redcode.server.constant.ServerErrorCode.*;

public class DateDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String value = jsonParser.getValueAsString();
        LocalDateTime result;

        try {
            result = LocalDateTime.parse(value);
        } catch (DateTimeParseException ex) {
            throw new ServerException(DATE_FORMAT_EXCEPTION);
        }

        return result;
    }

}

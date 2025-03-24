package club.benjifa.benjifa_backend_api.utils;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class OneDigitFloatSerializer extends JsonSerializer<Float> {
    @Override
    public void serialize(Float value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            BigDecimal bd = new BigDecimal(Float.toString(value));
            bd = bd.setScale(1, RoundingMode.HALF_UP);
            gen.writeNumber(bd);
        }
    }
}
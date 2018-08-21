package com.tick42.quicksilver.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Tag;

import java.io.IOException;

public class TagSerializer extends StdSerializer<Tag> {
    public TagSerializer() {
        this(null);
    }

    protected TagSerializer(Class<Tag> t) {
        super(t);
    }

    @Override
    public void serialize(Tag tag, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", tag.getId());
        jsonGenerator.writeStringField("name", tag.getName());

        jsonGenerator.writeArrayFieldStart("extensions");
        for (Extension extension: tag.getExtensions()) {
            jsonGenerator.writeObject(extension);
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}

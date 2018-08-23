package com.tick42.quicksilver.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.Tag;

import java.io.IOException;
import java.util.Set;

public class ExtensionSerializer extends StdSerializer<Extension> {

    public ExtensionSerializer() {
        this(null);
    }

    private ExtensionSerializer(Class<Extension> t) {
        super(t);
    }

    @Override
    public void serialize(Extension extension, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", extension.getId());
        jsonGenerator.writeStringField("title", extension.getName());
        jsonGenerator.writeNumberField("version", extension.getVersion());
        jsonGenerator.writeStringField("description", extension.getDescription());
        jsonGenerator.writeStringField("description", extension.getDescription());

        if (extension.getFile() != null) {
            jsonGenerator.writeObjectFieldStart("file");
            jsonGenerator.writeNumberField("id", extension.getFile().getId());
            jsonGenerator.writeStringField("location", extension.getFile().getLocation());
            jsonGenerator.writeStringField("name", extension.getFile().getName());
            jsonGenerator.writeEndObject();
        }

        if (extension.getImage() != null) {
            jsonGenerator.writeObjectFieldStart("image");
            jsonGenerator.writeNumberField("id", extension.getFile().getId());
            jsonGenerator.writeStringField("location", extension.getFile().getLocation());
            jsonGenerator.writeStringField("name", extension.getFile().getName());
            jsonGenerator.writeEndObject();
        }

        jsonGenerator.writeStringField("github", extension.getGithub());
        jsonGenerator.writeNumberField("timesDownloaded", extension.getTimesDownloaded());
        jsonGenerator.writeStringField("lastCommit", extension.getLastCommit().toString());
        jsonGenerator.writeStringField("uploadDate", extension.getUploadDate().toString());
        jsonGenerator.writeNumberField("pullRequests", extension.getPullRequests());
        jsonGenerator.writeNumberField("openIssues", extension.getOpenIssues());
        jsonGenerator.writeBooleanField("isPending", extension.getIsPending());
        jsonGenerator.writeBooleanField("isFeatured",extension.getIsFeatured());

        jsonGenerator.writeArrayFieldStart("tags");
        for (Tag tag : extension.getTags()) {
            jsonGenerator.writeString(tag.getName());
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeObjectFieldStart("owner");
        jsonGenerator.writeStringField("owner", extension.getOwner().getUsername());
        jsonGenerator.writeNumberField("id", extension.getOwner().getId());
        jsonGenerator.writeEndObject();

        jsonGenerator.writeEndObject();

    }
}

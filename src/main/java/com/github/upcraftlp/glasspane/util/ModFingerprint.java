package com.github.upcraftlp.glasspane.util;

import com.github.upcraftlp.glasspane.GlassPane;
import org.apache.commons.codec.digest.DigestUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Files;
import java.util.Set;

public class ModFingerprint implements Comparable<ModFingerprint> {

    private final File source;
    private final String expectedKey;
    private final Set<String> fingerPrints;
    private String md5Hash;

    public ModFingerprint(File source, @Nullable String expectedKey, Set<String> fingerPrints) {
        this.source = source;
        this.expectedKey = expectedKey;
        this.fingerPrints = fingerPrints;

    }

    public String getMd5Hash() {
        if(this.md5Hash == null) {
            if(!this.source.isDirectory()) {
                try {
                    this.md5Hash = DigestUtils.md5Hex(Files.readAllBytes(source.toPath()));
                } catch(Exception e) {
                    GlassPane.getLogger().error("error calculating file hash!", e);
                    this.md5Hash = "ERROR";
                }
            }
            else md5Hash = "DIRECTORY";
        }
        return this.md5Hash;
    }

    @Nullable
    public String getExpectedKey() {
        return expectedKey;
    }

    public Set<String> getFingerPrints() {
        return fingerPrints;
    }

    public File getSource() {
        return source;
    }

    @Override
    public int compareTo(ModFingerprint o) {
        return this.source.getName().compareTo(o.source.getName());
    }
}

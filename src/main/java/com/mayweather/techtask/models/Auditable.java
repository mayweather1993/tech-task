package com.mayweather.techtask.models;

import java.time.Instant;

public interface Auditable {
    void setCreatedDate(final Instant createdDate);
    void setLastModifiedDate(final Instant lastModifiedDate);
}

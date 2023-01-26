package net.catstack.retrotv.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdapterResponse<T> {
    private int status;
    private AdapterError error;
    private T response;

    public AdapterResponse(T response) {
        this.response = response;
    }
}

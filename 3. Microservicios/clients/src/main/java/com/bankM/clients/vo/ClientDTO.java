package com.bankM.clients.vo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {
    @NotBlank(message = "Nombre de cliente no puede ser vacío")
    @Size(message = "Nombre de cliente maximo de 128 caracteres y minimo 3",min= 3,max = 128)
    private String name;
    @NotBlank(message = "Ci de cliente no puede ser vacío")
    @Size(min=10, max=10, message = "Ci no valida, ingrese nuevamente")
    private String ci;
    @NotBlank(message = "Genero de cliente no puede ser vacío")
    @Size(message = "Genero de cliente maximo de 32 caracteres y minimo 1",min= 1,max = 32)
    private String gender;
    @NotNull
    @Max(message = "Edad de cliente maximo de 80 años", value = 80)
    @Min(message = "Edad de cliente minimo de 18 años", value = 18)
    private Integer age;
    @NotBlank(message = "Direccion de cliente no puede ser vacío")
    @Size(message = "Direccion de cliente maximo de 128 caracteres y minimo 10",min= 10,max = 128)
    private String address;
    @NotBlank(message = "Telefono de cliente no puede ser vacío")
    @Size(min=10, max=10, message = "Ingrese un número de teléfono válido")
    private String phone;
    @NotBlank(message = "Password de cliente no puede ser vacío")
    @Size(message = "Password de cliente maximo de 128 caracteres y minimo 5",min= 5,max = 128)
    private String password;
}

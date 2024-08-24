package net.javaguides.departmentservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        description = "DepartmentDto Model Information"
)

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
    private Long id;
    @Schema(
            description = "Department Name"
    )
    private String departmentName;
    @Schema(
            description = "Department Description"
    )
    private String departmentDescription;
    @Schema(
            description = "Department Code"
    )
    private String departmentCode;
}

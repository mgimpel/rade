/*  This file is part of the Rade project (https://github.com/mgimpel/rade).
 *  Copyright (C) 2018 Marc Gimpel
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
/* $Id$ */
package fr.aesn.rade.rs.dto;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.rs.RestService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Departement DTO for HATEOAS (Hypermedia As The Engine Of Application State)
 * REST Service.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Getter @Setter @NoArgsConstructor
public class HateoasDepartementDto
  extends DepartementDto {
  private List<Link> links;

  public static HateoasDepartementDto fromEntity(Departement dept, String basePath) {
    HateoasDepartementDto dto = new HateoasDepartementDto();
    dto.setAllfromEntity(dept);
    dto.setLinks(Arrays.asList(Link.fromData("self", URI.create(basePath + RestService.REST_PATH_DEPARTEMENT + dept.getCodeInsee())),
                               Link.fromData("region", URI.create(basePath + RestService.REST_PATH_REGION + dept.getRegion())),
                               Link.fromData("chef-lieu", URI.create(basePath + RestService.REST_PATH_COMMUNE + dept.getChefLieu()))));
    return dto;
  }
}

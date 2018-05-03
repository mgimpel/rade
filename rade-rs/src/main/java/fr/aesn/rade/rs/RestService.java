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
package fr.aesn.rade.rs;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.aesn.rade.persist.model.CirconscriptionBassin;
import fr.aesn.rade.persist.model.Commune;
import fr.aesn.rade.persist.model.Delegation;
import fr.aesn.rade.persist.model.Departement;
import fr.aesn.rade.persist.model.Region;
import fr.aesn.rade.rs.dto.CirconscriptionBassinListDto;
import fr.aesn.rade.rs.dto.CommuneListDto;
import fr.aesn.rade.rs.dto.DelegationListDto;
import fr.aesn.rade.rs.dto.DepartementListDto;
import fr.aesn.rade.rs.dto.HateoasCirconscriptionBassinDto;
import fr.aesn.rade.rs.dto.HateoasCommuneDto;
import fr.aesn.rade.rs.dto.HateoasDelegationDto;
import fr.aesn.rade.rs.dto.HateoasDepartementDto;
import fr.aesn.rade.rs.dto.HateoasRegionDto;
import fr.aesn.rade.rs.dto.RegionListDto;
import fr.aesn.rade.service.BassinService;
import fr.aesn.rade.service.CommuneService;
import fr.aesn.rade.service.DelegationService;
import fr.aesn.rade.service.DepartementService;
import fr.aesn.rade.service.RegionService;
import lombok.Setter;

/**
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
public class RestService {
  /** SLF4J Logger. */
  private static final Logger log =
    LoggerFactory.getLogger(RestService.class);
  /** Region Service. */
  @Setter
  private RegionService regionService;
  /** Departement Service. */
  @Setter
  private DepartementService departementService;
  /** Commune Service. */
  @Setter
  private CommuneService communeService;
  /** Delegation Service. */
  @Setter
  private DelegationService delegationService;
  /** Bassin Service. */
  @Setter
  private BassinService bassinService;

  /** Relative path of the REST Service for Audit. */
  public static final String REST_PATH_AUDIT       = "audit/";
  /** Relative path of the REST Service for Region. */
  public static final String REST_PATH_REGION      = "region/";
  /** Relative path of the REST Service for Departement. */
  public static final String REST_PATH_DEPARTEMENT = "departement/";
  /** Relative path of the REST Service for Commune. */
  public static final String REST_PATH_COMMUNE     = "commune/";
  /** Relative path of the REST Service for Delegation. */
  public static final String REST_PATH_DELEGATION  = "delegation/";
  /** Relative path of the REST Service for Circonscription Bassin. */
  public static final String REST_PATH_CIRCONSCRIPTION_BASSIN  = "bassin/";

  @GET
  @Path(REST_PATH_REGION)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllRegion(@Context final HttpServletRequest req,
                               @QueryParam("date") final String rawdate) {
    log.info("Executing operation getAllRegion with date {}", rawdate);
    try {
      Date date = checkDate(rawdate);
      List<Region> regions = regionService.getAllRegion(date);
      if (regions.size() > 0) {
        return Response.ok(RegionListDto.fromEntityList(regions))
                       .build();
      } else {
        return Response.status(Response.Status.NOT_FOUND)
                       .build();
      }
    }
    catch (RestRequestException e) {
      log.error(e.getMessage(), e);
      return Response.status(Response.Status.BAD_REQUEST)
                     .entity(e.getMessage())
                     .build();
    }
  }

  @GET
  @Path(REST_PATH_REGION + "{code}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getRegion(@Context final HttpServletRequest req,
                            @PathParam("code") final String rawcode,
                            @QueryParam("date") final String rawdate) {
    log.info("Executing operation getRegion with code {} and date {}", rawcode, rawdate);
    try {
      String code = checkCode(rawcode);
      Date date = checkDate(rawdate);
      Region region = regionService.getRegionByCode(code, date);
      if (region != null) {
        return Response.ok(HateoasRegionDto.fromEntity(region, getRestBasePath(req, REST_PATH_REGION)))
                       .build();
      } else {
        return Response.status(Response.Status.NOT_FOUND)
                       .build();
      }
    }
    catch (RestRequestException e) {
      log.error(e.getMessage(), e);
      return Response.status(Response.Status.BAD_REQUEST)
                     .entity(e.getMessage())
                     .build();
    }
  }

  @GET
  @Path(REST_PATH_DEPARTEMENT)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllDepartement(@Context final HttpServletRequest req,
                                    @QueryParam("date") final String rawdate) {
    log.info("Executing operation getAllDepartement with date {}", rawdate);
    try {
      Date date = checkDate(rawdate);
      List<Departement> depts = departementService.getAllDepartement(date);
      if (depts.size() > 0) {
        return Response.ok(DepartementListDto.fromEntityList(depts))
                       .build();
      } else {
        return Response.status(Response.Status.NOT_FOUND)
                       .build();
      }
    }
    catch (RestRequestException e) {
      log.error(e.getMessage(), e);
      return Response.status(Response.Status.BAD_REQUEST)
                     .entity(e.getMessage())
                     .build();
    }
  }

  @GET
  @Path(REST_PATH_DEPARTEMENT + "{code}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDepartement(@Context final HttpServletRequest req,
                                 @PathParam("code") final String rawcode,
                                 @QueryParam("date") final String rawdate) {
    log.info("Executing operation getDepartement with code {} and date {}", rawcode, rawdate);
    try {
      String code = checkCode(rawcode);
      Date date = checkDate(rawdate);
      Departement dept = departementService.getDepartementByCode(code, date);
      if (dept != null) {
        return Response.ok(HateoasDepartementDto.fromEntity(dept, getRestBasePath(req, REST_PATH_DEPARTEMENT)))
                       .build();
      } else {
        return Response.status(Response.Status.NOT_FOUND)
                       .build();
      }
    }
    catch (RestRequestException e) {
      log.error(e.getMessage(), e);
      return Response.status(Response.Status.BAD_REQUEST)
                     .entity(e.getMessage())
                     .build();
    }
  }

  @GET
  @Path(REST_PATH_COMMUNE)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllCommune(@Context final HttpServletRequest req,
                                @QueryParam("date") final String rawdate) {
    log.info("Executing operation getAllCommune with date {}", rawdate);
    try {
      Date date = checkDate(rawdate);
      List<Commune> communes = communeService.getAllCommune(date);
      if (communes.size() > 0) {
        return Response.ok(CommuneListDto.fromEntityList(communes))
                       .build();
      } else {
        return Response.status(Response.Status.NOT_FOUND)
                       .build();
      }
    }
    catch (RestRequestException e) {
      log.error(e.getMessage(), e);
      return Response.status(Response.Status.BAD_REQUEST)
                     .entity(e.getMessage())
                     .build();
    }
  }

  @GET
  @Path(REST_PATH_COMMUNE + "{code}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getCommune(@Context final HttpServletRequest req,
                             @PathParam("code") final String rawcode,
                             @QueryParam("date") final String rawdate) {
    log.info("Executing operation getCommune with code {} and date {}", rawcode, rawdate);
    try {
      String code = checkCode(rawcode);
      Date date = checkDate(rawdate);
      Commune commune = communeService.getCommuneByCode(code, date);
      if (commune != null) {
        return Response.ok(HateoasCommuneDto.fromEntity(commune, getRestBasePath(req, REST_PATH_COMMUNE)))
                       .build();
      } else {
        return Response.status(Response.Status.NOT_FOUND)
                       .build();
      }
    }
    catch (RestRequestException e) {
      log.error(e.getMessage(), e);
      return Response.status(Response.Status.BAD_REQUEST)
                     .entity(e.getMessage())
                     .build();
    }
  }

  @GET
  @Path(REST_PATH_DELEGATION)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllDelegation(@Context final HttpServletRequest req) {
  log.info("Executing operation getAllDelegation");
    List<Delegation> delegations = delegationService.getAllDelegation();
    if (delegations.size() > 0) {
      return Response.ok(DelegationListDto.fromEntityList(delegations))
                     .build();
    } else {
      return Response.status(Response.Status.NOT_FOUND)
                     .build();
    }
  }

  @GET
  @Path(REST_PATH_DELEGATION + "{code}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDelegation(@Context final HttpServletRequest req,
                                @PathParam("code") final String rawcode) {
    log.info("Executing operation getDelegation with code {}", rawcode);
    try {
      String code = checkCode(rawcode);
      Delegation delegation = delegationService.getDelegationById(code);
      if (delegation != null) {
        return Response.ok(HateoasDelegationDto.fromEntity(delegation, getRestBasePath(req, REST_PATH_DELEGATION)))
                       .build();
      } else {
        return Response.status(Response.Status.NOT_FOUND)
                       .build();
      }
    }
    catch (RestRequestException e) {
      log.error(e.getMessage(), e);
      return Response.status(Response.Status.BAD_REQUEST)
                     .entity(e.getMessage())
                     .build();
    }
  }

  @GET
  @Path(REST_PATH_CIRCONSCRIPTION_BASSIN)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllBassin(@Context final HttpServletRequest req) {
  log.info("Executing operation getAllBassin");
    List<CirconscriptionBassin> bassins = bassinService.getAllBassin();
    if (bassins.size() > 0) {
      return Response.ok(CirconscriptionBassinListDto.fromEntityList(bassins))
                     .build();
    } else {
      return Response.status(Response.Status.NOT_FOUND)
                     .build();
    }
  }

  @GET
  @Path(REST_PATH_CIRCONSCRIPTION_BASSIN + "{code}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getBassin(@Context final HttpServletRequest req,
                            @PathParam("code") final String rawcode) {
    log.info("Executing operation getBassin with code {}", rawcode);
    try {
      String code = checkCode(rawcode);
      CirconscriptionBassin bassin = bassinService.getBassinByCode(code);
      if (bassin != null) {
        return Response.ok(HateoasCirconscriptionBassinDto.fromEntity(bassin, getRestBasePath(req, REST_PATH_CIRCONSCRIPTION_BASSIN)))
                       .build();
      } else {
        return Response.status(Response.Status.NOT_FOUND)
                       .build();
      }
    }
    catch (RestRequestException e) {
      log.error(e.getMessage(), e);
      return Response.status(Response.Status.BAD_REQUEST)
                     .entity(e.getMessage())
                     .build();
    }
  }

  private static final String checkCode(final String rawcode)
    throws RestRequestException {
    String code = null;
    try {
      code = URLDecoder.decode(rawcode, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RestRequestException("Could not decode " + rawcode, e);
    }
    return code;
  }

  private static final Date checkDate(final String rawdate)
    throws RestRequestException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    if (rawdate == null) {
      date = new Date();
    } else {
      try {
        date = sdf.parse(rawdate);
      }
      catch (ParseException e) {
        throw new RestRequestException("Could not parse date " + rawdate, e);
      }
    }
    return date;
  }

/*
  private static final String getPathFromAnnotation() {
    try {
      String caller = Thread.currentThread().getStackTrace()[2].getMethodName();
      Method m = RestService.class.getMethod(caller);
      Path a = m.getAnnotation(Path.class);
      if (a == null) {
        log.error("Called from a method without the @Path annotation.");
        return null;
      }
      return a.value();
    }
    catch (NoSuchMethodException e) {
      log.error("This should never happen (private function so caller method should exist in class)");
      return null;
    }
  }
*/
  private static final String getRestBasePath(final HttpServletRequest req, final String servicePath) {
    String url = req.getRequestURL().toString();
    int index = url.indexOf(servicePath);
    if (index < 0) {
      log.error("Request ({}) not from declared service: {}", url, servicePath);
      return url;
    }
    return url.substring(0, index);
  }
}

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
package fr.aesn.rade.batch.tasks.insee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model for an INSEE Commune History record.
 * 
 * Example file:
 * <code>
 * DEP AR CT COM LEG         JO         EFF        DTR        MOD C_LOFF C_LANC NBCOM RANGCOM COMECH POPECH SUECH DEPANC ARRANC CTANC TNCCOFF NCCOFF            TNCCANC NCCANC
 * 01        003 A16-07-1973 19-08-1973 01-01-1974 01-01-1974 330                             01165                                   1       Amareins
 * 01        003 A07-12-1982            01-01-1983 01-01-1983 350                             01165                                   1       Amareins
 * 01  1  01 004 D25-03-1955 30-03-1955 31-03-1955 31-03-1955 100                                                                     1       Ambérieu-en-Bugey 1       Ambérieu
 * 01  4  15 014 D17-12-1996 24-12-1996 01-01-1997 01-01-1997 610                             01283  0      0                         1       Arbent
 * 01  4  15 014 D17-12-1996 24-12-1996 01-01-1997 01-01-1997 630                             01283  0      0                         1       Arbent
 * 01     04 015 A29-09-2015 24-12-2015 01-01-2016 01-01-2016 331                             01015                                   1       Arbignieu
 * 01  1  04 015 A29-09-2015 24-12-2015 01-01-2016 01-01-2016 341               2     2       01340                                   1       Arboys en Bugey
 * 01  1  04 015 A29-09-2015 24-12-2015 01-01-2016 01-01-2016 341               2     1       01015                                   1       Arboys en Bugey
 * 01        018 A09-12-1970 29-12-1970 01-01-1971 01-01-1971 310                             01033                                   1       Arlod
 * </code>
 * Columns Description:
 * <table>
 * <tr><th>Longueur</th>  <th>Nom</th>    <th>Désignation en clair</th></tr>
 * <tr><td>3</td>         <td>DEP</td>    <td>Code département</td></tr>
 * <tr><td>1</td>         <td>AR</td>     <td>Code arrondissement</td></tr>
 * <tr><td>2</td>         <td>CT</td>     <td>Code canton</td></tr>
 * <tr><td>3</td>         <td>COM</td>    <td>Code commune</td></tr>
 * <tr><td>4 x (1+10)</td><td>LEG</td>    <td>Type et date de texte (4 possibilités)</td></tr>
 * <tr><td>4 x 10</td>    <td>JO</td>     <td>Date de parution au J.O. (4 possibilités)</td></tr>
 * <tr><td>10</td>        <td>EFF</td>    <td>Date d'effet</td></tr>
 * <tr><td>10</td>        <td>DTR</td>    <td>Date la plus récente</td></tr>
 * <tr><td>3</td>         <td>MOD</td>    <td>Type de modification</td></tr>
 * <tr><td>5</td>         <td>C-LOFF</td> <td>Commune chef-lieu de commune officielle (MOD = 500, 510, 520, 530, 540)</td></tr>
 * <tr><td>5</td>         <td>C-LANC</td> <td>Ancienne commune chef-lieu (MOD = 500, 510, 520, 530, 540)</td></tr>
 * <tr><td>2</td>         <td>NBCOM</td>  <td>Nombre de communes ayant participé à la création de la commune (MOD = 200) ou ayant fusionné avec la commune absorbante (MOD = 320, 321, 340, 341) ou ayant reçu des parcelles (MOD = 300)</td></tr>
 * <tr><td>2</td>         <td>RANGCOM</td><td>Rang de la commune ayant participé à la création de la commune (MOD = 200) ou ayant fusionné avec la commune absorbante (MOD = 320, 321, 340, 341) ou ayant reçu des parcelles (MOD = 300)</td></tr>
 * <tr><td>5</td>         <td>COMECH</td> <td>Code de la commune, dans le cas où : MOD = 200, ayant donné des parcelles, MOD = 210, pôle avant le rétablissement, MOD = 220, ayant reçu des parcelles, MOD = 230, associée avant rétablissement, MOD = 300, ayant reçu des parcelles, MOD = 310, 311, 330, 331, 332, 333, 350, 351 de rattachement, MOD = 320, 321, 340, 341, 360, rattachée, MOD = 390, ayant donné des parcelles, MOD = 600, 610, ayant reçu une parcelle, MOD = 620, 630, ayant donné une parcelle</td></tr>
 * <tr><td>8</td>         <td>POPECH</td> <td>Population donnée par la commune COMECH dans le cas (MOD = 620) ou reçue par la commune COMECH dans le cas (MOD = 600)</td></tr>
 * <tr><td>8</td>         <td>SUECH</td>  <td>Surface donnée par la commune COMECH dans le cas (MOD = 620, 630) ou reçue par la commune COMECH dans le cas (MOD = 600, 610)</td></tr>
 * <tr><td>5</td>         <td>DEPANC</td> <td>Ancien code de la commune suite au changement de département (MOD = 410, 411)</td></tr>
 * <tr><td>4</td>         <td>ARRANC</td> <td>Ancien arrondissement d'appartenance (MOD = 420, 421)</td></tr>
 * <tr><td>5</td>         <td>CTANC</td>  <td>Ancien canton d'appartenance (MOD = 430, 431)</td></tr>
 * <tr><td>1</td>         <td>TNCCOFF</td><td>Type de nom en clair</td></tr>
 * <tr><td>70</td>        <td>NCCOFF</td> <td>Nom officiel en clair, après l'évènement</td></tr>
 * <tr><td>1</td>         <td>TNCCANC</td><td>Type d'ancien nom (MOD = 100, 110, 111, 120, 130, 140)</td></tr>
 * <tr><td>70</td>        <td>NCCANC</td> <td>Ancien nom (MOD = 100, 110, 111, 120, 130, 140)</td></tr>
 * </table>
 * 
 * For more details, see
 * https://www.insee.fr/fr/information/3363419
 * 
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Getter @Setter @NoArgsConstructor
@ToString @EqualsAndHashCode
public class HistoriqueCommuneInseeModel implements Serializable {
  /** Unique Identifier for Serializable Class. */
  private static final long serialVersionUID = 332832229694762679L;

  /** DEP - Code département. */
  @Size(max = 3)
  private String codeDepartement;

  /** AR - Code arrondissement. */
  @Size(max = 1)
  private String codeArrondissement;

  /** CT - Code canton. */
  @Size(max = 2)
  private String codeCanton;

  /** COM - Code commune. */
  @Size(max = 3)
  private String codeCommune;

  /** LEG - Texte Legislative. */
  @Size(max = 47)
  private String texteLegislative;

  /** JO - Date de parution au Journal Officiel. */
  @Size(min=0, max=4)
  private List<Date> dateJO;

  @NotNull
  /** EFF - Date d'effet. */
  private Date dateEffet;

  /** DTR - Date la plus récente. */
  private Date datePlusRecente;

  /** MOD - Type de modification. */
  @Size(max = 3)
  private String typeModification;

  /** C-LOFF - Commune chef-lieu de commune officielle. */
  @Size(max = 5)
  private String chefLieu;

  /** C-LANC - Ancienne commune chef-lieu. */
  @Size(max = 5)
  private String ancienChefLieu;

  /** NBCOM - Nombre de communes ayant participé. */
  @Min(0) @Max(99)
  private Integer nombreCommunes;

  /** RANGCOM - Rang de communes ayant participé. */
  @Min(0) @Max(99)
  private Integer rangCommunes;

  /** COMECH - Code de la commune associée. */
  @Size(max = 5)
  private String communeEchange;

  /** POPECH - Population de la commune associée. */
  @Min(0) @Max(99999999)
  private Integer populationCommuneEchange;

  /** SUECH - Surface de la commune associée. */
  @Min(0) @Max(99999999)
  private Integer surfaceCommuneEchange;

  /** DEPANC - Ancien code de la commune suite au changement de département. */
  @Size(max = 5)
  private String ancienCommuneChgmtDept;

  /** ARRANC - Ancien arrondissement d'appartenance. */
  @Size(max = 4)
  private String ancienArrondissement;

  /** CTANC - Ancien canton d'appartenance. */
  @Size(max = 5)
  private String ancienCanton;

  /** TNCCOFF - Type de nom en clair. */
  @Size(max = 1)
  private String typeNomClair;

  /** NCCOFF - Nom officiel en clair, après l'évènement. */
  @Size(max = 70)
  private String nomOfficiel;

  /** TNCCANC - Ancien type de nom en clair. */
  @Size(max = 1)
  private String ancienTypeNomClair;

  /** NCCANC - Ancien Nom. */
  @Size(max = 70)
  private String ancienNom;

  /**
   * Pair of INSEE Commune History records that are associated.
   * @author Marc Gimpel (mgimpel@gmail.com)
   */
  @Getter
  public static class Pair {
    /** Parent INSEE Commune History record. */
    private HistoriqueCommuneInseeModel parent;
    /** Child INSEE Commune History record. */
    private HistoriqueCommuneInseeModel enfant;

    /**
     * Full Constructor.
     * @param parent Parent INSEE Commune History record.
     * @param enfant Child INSEE Commune History record.
     */
    public Pair(HistoriqueCommuneInseeModel parent,
                HistoriqueCommuneInseeModel enfant) {
      this.parent = parent;
      this.enfant = enfant;
    }

    /**
     * Pair is considered valid if the effective date of the change is the same
     * and the Commune d'Echange (COMECH) field of one corresponds to the Code
     * INSEE Commune of the other.
     * @return true if valid, false otherwise.
     */
    public boolean isValid() {
      return (parent != null && enfant != null
        && parent.getCommuneEchange() != null
        && enfant.getCommuneEchange() != null
        && parent.getDateEffet().equals(enfant.getDateEffet())
        && parent.getCommuneEchange().equals(enfant.getCodeDepartement()
                                           + enfant.getCodeCommune())
        && enfant.getCommuneEchange().equals(parent.getCodeDepartement()
                                           + parent.getCodeCommune()));
    }

    /**
     * Returns the effective date of the Pair, or null if the Pair isn't valid.
     * @return the effective date of the Pair, or null if the Pair isn't valid.
     */
    public Date getDateEffet() {
      return isValid() ? parent.getDateEffet() : null;
    }
  }

  /**
   * Set of INSEE Commune History Pairs that are associated into one Changeset.
   * @author Marc Gimpel (mgimpel@gmail.com)
   */
  @Getter
  public static class Changeset {
    /** Set of INSEE Commune History Pairs that make up the Changeset. */
    private List<Pair> pairs;

    /**
     * Basic Constructor.
     */
    public Changeset() {
      pairs = new ArrayList<>();
    }

    /**
     * Append all of the Pairs in the given collection to the end of the list.
     * @param collection Pairs to be appended to the end of the list.
     */
    public void addAll(Collection<Pair> collection) {
      pairs.addAll(collection);
    }

    /**
     * Changeset is considered valid if all the Pairs are associated, in
     * particular if they all have the same effective date, and their number
     * all correspond to their NBCOM field.
     * @return true if valid, false otherwise.
     */
    public boolean isValid() {
      if (pairs.isEmpty()) {
        return false;
      }
      Date eff = pairs.get(0).getDateEffet();
      Integer nbcom = pairs.get(0).getParent().getNombreCommunes();
      if (eff == null || nbcom == null) {
        return false;
      }
      if (pairs.size() != nbcom 
          && !"Roche-sur-Yon".equals(pairs.get(0).getParent().getNomOfficiel())) {
        // Le 25/08/1964 Saint-André-d'Ornay (85195) et Bourg-sous-la-Roche-sur-Yon (85032)
        // ont fusionné avec Roche-sur-Yon (85191) mais tous les deux sont de rang=1 & nb=1
        return false;
      }
      for (Pair pair : pairs) {
        if (!pair.isValid()
          || !eff.equals(pair.getDateEffet())
          || !nbcom.equals(pair.getParent().getNombreCommunes())) {
          return false;
        }
      }
      return true;
    }

    /**
     * Returns the effective date of the Set, or null if the Set isn't valid.
     * @return the effective date of the Set, or null if the Set isn't valid.
     */
    public Date getDateEffet() {
      return isValid() ? pairs.get(0).getDateEffet() : null;
    }
  }
}

/*******************************************************************************
 * @author Copyright (C) 2020 ICreated, Sergey Polyarus
 * @date 2020 This program is free software; you can redistribute it and/or modify it under the
 *       terms version 2 of the GNU General Public License as published by the Free Software
 *       Foundation. This program is distributed in the hope that it will be useful, but WITHOUT ANY
 *       WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *       PURPOSE. See the GNU General Public License for more details. You should have received a
 *       copy of the GNU General Public License along with this program; if not, write to the Free
 *       Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 ******************************************************************************/
package co.icreated.portal.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenDto {

  @JsonProperty("token")
  String token = null;

  public TokenDto() {}

  public TokenDto(String token) {
    super();
    this.token = token;
  }


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }


}

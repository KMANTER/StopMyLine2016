/**
 * This file is part of WANTED: Bad-ou-Alyve.
 *
 * WANTED: Bad-ou-Alyve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WANTED: Bad-ou-Alyve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WANTED: Bad-ou-Alyve.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.skmjbfd.stopmyline.model;

public class User {

    public String name;
    public String token;

    public int rowCount;
    public int highestBlock;

    public User(String name, String token){
        this.name = name;
        this.token = token;
        this.rowCount = 0;
        this.highestBlock = 0;

    }

//    public User copy() {
//        User user = new User();

//        user.name = name;
//        user.token = token;
//        user.rowCount = 0;
//        user.highestBlock = 0;
//        user.lastUpdateTime = lastUpdateTime;

//        return user;
//    }
}

/**
 *  Copyright 2019 Martynas Jusevičius <martynas@atomgraph.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.atomgraph.linkeddatahub.server.filter.request;

import com.atomgraph.linkeddatahub.server.model.ClientUriInfo;
import com.atomgraph.linkeddatahub.server.model.impl.ClientUriInfoImpl;
import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;

/**
  * Request filter that sets request attribute with name <code>lapp:Context</code> and current context as the value
  * 
 * @author Martynas Jusevičius {@literal <martynas@atomgraph.com>}
 */
@PreMatching
@Priority(500)
public class ClientUriInfoFilter implements ContainerRequestFilter
{

    @Override
    public void filter(ContainerRequestContext request) throws IOException
    {
        // we need to save the current URI state somewhere, as it will be overridden by app base URI etc.
        if (request.getProperty(ClientUriInfo.class.getName()) == null)
        {
            ClientUriInfo clientUriInfo = new ClientUriInfoImpl(request.getUriInfo().getBaseUri(), request.getUriInfo().getRequestUri(), request.getUriInfo().getQueryParameters());
            request.setProperty(ClientUriInfo.class.getName(), clientUriInfo); // used in ClientUriInfoProvider
        }
    }
    
}

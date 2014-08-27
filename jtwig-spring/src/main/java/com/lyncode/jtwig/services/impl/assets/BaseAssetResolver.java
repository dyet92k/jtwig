/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lyncode.jtwig.services.impl.assets;

import com.lyncode.jtwig.exceptions.AssetResolveException;
import com.lyncode.jtwig.mvc.JtwigViewResolver;
import com.lyncode.jtwig.services.api.assets.AssetResolver;
import com.lyncode.jtwig.util.LocalThreadHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.context.Theme;
import org.springframework.web.servlet.ViewResolver;

import static com.lyncode.jtwig.util.FilePath.path;
import static org.springframework.web.servlet.support.RequestContextUtils.getTheme;

public class BaseAssetResolver implements AssetResolver {
    private String prefix;

    @Autowired
    private ViewResolver viewResolver;

    @Override
    public String resolve(String asset) throws AssetResolveException {
        if (prefix == null) prefix = "/";
        if (!(viewResolver instanceof JtwigViewResolver))
            throw new AssetResolveException("The view resolver must be a JtwigViewResolver");
        else {
            Theme theme = getTheme(LocalThreadHolder.getServletRequest());
            if (theme != null) {
                return path(prefix).append(theme.getName()).append(asset).toString();
            } else {
                return path(prefix).append(asset).toString();
            }
        }
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}

/*
 * Copyright 2008 Alin Dreghiciu.
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.url.mvn.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.ops4j.lang.NullArgumentException;
import org.ops4j.net.URLUtils;

/**
 * An artifact that can be downloaded.
 *
 * @author Alin Dreghiciu
 * @since 0.2.0, January 30, 2008
 */
public class DownloadableArtifact
{

    /**
     * Artifact version.
     */
    private final Version m_version;
    /**
     * The full url from where the artifact can be downloaded.
     */
    private final URL m_artifactURL;
    /**
     * true if the certificate should be checked on SSL connection, false otherwise.
     */
    private final Boolean m_checkCertificate;

    /**
     * Creates a new downloadable artifact.
     *
     * @param version          artifact version. Cannot be null or empty
     * @param repositoryURL    url to reporsitory. Cannot be null
     * @param path             a path to the artifact jar file. Cannot be null
     * @param checkCertificate if the certificate should be checked on an SSL connection. Cannot be null
     *
     * @throws java.io.IOException   re-thrown
     * @throws NullArgumentException if any of the parameters is null or version is empty
     */
    DownloadableArtifact( final String version,
                          final URL repositoryURL,
                          final String path,
                          final Boolean checkCertificate )
        throws IOException
    {
        NullArgumentException.validateNotEmpty( version, "Version" );
        NullArgumentException.validateNotNull( repositoryURL, "Repository URL" );
        NullArgumentException.validateNotNull( path, "Path" );
        NullArgumentException.validateNotNull( checkCertificate, "Certificate check" );

        m_version = new Version( version );
        String repository = repositoryURL.toExternalForm();
        if( !repository.endsWith( Parser.FILE_SEPARATOR ) )
        {
            repository = repository + Parser.FILE_SEPARATOR;
        }
        m_artifactURL = new URL( repository + path );
        m_checkCertificate = checkCertificate;
    }

    /**
     * Return the input stream to artifact.
     *
     * @return prepared input stream
     *
     * @throws IOException re-thrown
     * @see org.ops4j.net.URLUtils#prepareInputStream(java.net.URL,boolean)
     */
    InputStream getInputStream()
        throws IOException
    {
        return URLUtils.prepareInputStream( m_artifactURL, !m_checkCertificate );
    }

    /**
     * Getter.
     *
     * @return artifact version
     */
    public Version getVersion()
    {
        return m_version;
    }

    @Override
    public boolean equals( Object o )
    {
        if( this == o )
        {
            return true;
        }
        if( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        return m_version.equals( ( (DownloadableArtifact) o ).m_version );

    }

    @Override
    public int hashCode()
    {
        return m_version.hashCode();
    }
}

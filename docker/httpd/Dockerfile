FROM httpd:2.4-alpine

RUN sed -i 's/^#\(LoadModule proxy_module\)/\1/' /usr/local/apache2/conf/httpd.conf \
    && sed -i 's/^#\(LoadModule proxy_http_module\)/\1/' /usr/local/apache2/conf/httpd.conf \
    && sed -i 's/^#\(LoadModule headers_module\)/\1/' /usr/local/apache2/conf/httpd.conf

COPY docker/httpd/proxy.conf /usr/local/apache2/conf/extra/proxy.conf
RUN echo 'Include conf/extra/proxy.conf' >> /usr/local/apache2/conf/httpd.conf

COPY docker/apache-client/ /usr/local/apache2/htdocs/

const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = function(app) {
  app.use(
    "/api",
    createProxyMiddleware({
      target: process.env.BACKEND_URL,
      pathRewrite: { "^/api": "" },
      logger: console,
      onProxyReq:function onProxyReq(proxyReq, req, res) {
        // Log outbound request to remote target
        console.log('-->  ', req.method,req.path, '->', process.env.BACKEND_URL + proxyReq.path);
    }
      
    })
  );
  if(process.env.PUBLIC_URL){
    app.use(
      `${process.env.PUBLIC_URL}/api`,
      createProxyMiddleware({
        target: process.env.BACKEND_URL,
        pathRewrite: function (path, req) { return path.replace(`${process.env.PUBLIC_URL}/api`, '') },
        logger: console,
        onProxyReq:function onProxyReq(proxyReq, req, res) {
          // Log outbound request to remote target
          console.log('-->  ', req.method,req.path, '->', process.env.BACKEND_URL + proxyReq.path);
      }
        
      })
    );
  }
  
};

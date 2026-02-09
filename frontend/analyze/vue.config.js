module.exports = {
    devServer: {
        port: 8080,
        proxy: {
            '/api/v2': {
                target: 'http://localhost:8085',
                secure: false,
                changeOrigin: true,
                pathRewrite: {
                    '^/api/v2': '/'
                }
            }
        }
    }
}
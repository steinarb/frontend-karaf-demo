var path = require('path');
const ESLintPlugin = require('eslint-webpack-plugin');

const PATHS = {
    build: path.join(__dirname, '..', '..', '..', 'target', 'classes')
};

module.exports = {
    mode: 'development',
    entry: './src/index.js',
    output: {
        path: PATHS.build,
        filename: 'bundle.js'
    },
    resolve: {
        extensions: ['.js', '.jsx']
    },
    plugins: [new ESLintPlugin()],
    module: {
        rules: [
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                use: ['babel-loader?' + JSON.stringify({
                    cacheDirectory: true,
                    presets: ['@babel/preset-react']
                })],
            },
        ]
    }
};

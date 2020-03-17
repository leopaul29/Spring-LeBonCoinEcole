var path = require('path');

module.exports = {
    entry: './src/main/js/app.js',
    //Creates sourcemaps so that, when you are debugging JS code in the browser, you can link back to original source code
    devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    output: {
        path: __dirname,
        //Compile ALL of the JavaScript bits into
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                //It hooks into the babel engine, using both es2015 and react presets,
                //in order to compile ES6 React code into a format able to be run in any standard browser.
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            }
        ]
    }
};
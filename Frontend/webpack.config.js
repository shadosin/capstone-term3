const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
  optimization: {
    usedExports: true
  },
  entry: {
    examplePage: path.resolve(__dirname, 'src', 'pages', 'examplePage.js'),
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].js',
  },
  devServer: {
    https: false,
    port: 8080,
    open: true,
    //proxy is what tells your frontend where to find the backend and what requests to send there
    //if you  notice in the example we are sending all requests that start with /example to
    //http://localhost:5001 which is where the backend is, when sent to the backend it will become
    //http://localhost:5001/exemple/...
    //for example if you sent the request /example/bob to the backend, it will be converted into
    //http://localhost:5001/example/bob and sent to the backend that way.
    //uncomment the following proxy section to make the example work
//    proxy: [
//          {
//            context: [
//              '/example',
//            ],
//            target: 'http://localhost:5001'
//          }
//        ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/index.html',
      filename: 'index.html',
      inject: false
    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/css'),
          to: path.resolve("dist/css")
        }
      ]
    }),
    new CleanWebpackPlugin()
  ]
}

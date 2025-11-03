module.exports = {
  presets: ['@babel/preset-env', '@babel/preset-react'],
  plugins: [
    ['@babel/plugin-proposal-class-properties', { spec: true }],
    '@babel/plugin-transform-async-to-generator',
    '@babel/plugin-proposal-object-rest-spread',
    '@babel/plugin-syntax-dynamic-import',
    'dynamic-import-node',
    '@babel/plugin-transform-modules-commonjs',
    'react-hot-loader/babel',
  ],
  ignore: ['**/*.json'],
};

// java {
//     toolchain {
//         languageVersion = JavaLanguageVersion.of(16, 0, 0, 36)
//         vendor = JvmVendorSpec.ADOPTOPENJDK
//     }
// }

import postcssImport from 'postcss-import';
import postcssMixins from 'postcss-mixins';
import postcssSimpleVars from 'postcss-simple-vars';
import autoprefixer from 'autoprefixer';

export default {
  plugins: [
    postcssImport,
    postcssMixins,
    postcssSimpleVars,
    autoprefixer
  ]
};

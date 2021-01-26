/* eslint-disable */
const path = require("path");
const withLess = require("@zeit/next-less");
const withImages = require("next-images");
const withPlugins = require("next-compose-plugins");

if (typeof require !== "undefined") {
  require.extensions[".less"] = () => {};
  require.extensions[".css"] = () => {};
}

const RESOLVE_ALIASES = {
  components: "components",
  constants: "constants",
  form: "components/form",
  hooks: "hooks",
};

const addResolveAlias = (config, name, directory) => {
  config.resolve.alias[name] = path.join(__dirname, `src/${directory}`);
};

module.exports = withPlugins(
  [
    [
      withLess,
      {
        lessLoaderOptions: {
          javascriptEnabled: true,
        },
      },
    ],
    [withImages],
  ],
  {
    webpack: (config, { isServer }) => {
      Object.entries(RESOLVE_ALIASES).forEach(([name, directory]) => addResolveAlias(config, name, directory));

      if (isServer) {
        const antStyles = /antd\/.*?\/style.*?/;
        const origExternals = [...config.externals];

        config.externals = [
          (context, request, callback) => {
            if (request.match(antStyles)) return callback();

            if (typeof origExternals[0] === "function") {
              origExternals[0](context, request, callback);
            } else {
              callback();
            }
          },
          ...(typeof origExternals[0] === "function" ? [] : origExternals),
        ];

        config.module.rules.unshift({
          test: antStyles,
          use: "null-loader",
        });
      }

      return config;
    },
  }
);

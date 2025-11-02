# Repro - Promesa resolve stack overflow

This is the currently minimal repro for a stack overflow when using [Promesa](https://github.com/funcool/promesa) to resolve a promise returned by `(vscode/env.clipboard.readText)` in a VS Code extension.

The latest Promesa version where this does not occur is `10.0.594`. The issue appears starting from `11.0.664`.

The code reproducing the issue is in [src/repro/extension.cljs](src/repro/extension.cljs):

```clojure
(defn say-clipboard-content! []
  (p/let [; text (.then (vscode/env.clipboard.readText) identity) ; works fine
          text (vscode/env.clipboard.readText) ; crashes
          ]
    (vscode/window.showInformationMessage (str "clipboard contains: " text))))
```

## Steps to Reproduce

1. Clone this repository
2. `npm install`
3. `npx shadow-cljs compile :extension`
4. Open the folder in VS Code
5. Press `F5` to launch the extension in a new VS Code window
6. In the development window, open the command palette and run the command `Repro: Say Clipboard!`

# Enthalpy

A ClojureScript rewrite of the ColemakCamp typing trainer, re‑focused around
the Enthium v14 keyboard layout.  The site is a static single‑page application
compiled with shadow‑cljs and served from the `docs/` directory (GitHub Pages).

The primary feature is an interactive typing trainer with progressive levels,
a live cheatsheet keyboard and real‑time WPM/accuracy scoring.  Secondary
pages provide layout rationale, performance statistics, a comparison table,
and learning documentation, all reachable via hash routes.

The original ColemakCamp JavaScript source is kept in the repository root for
reference only; the active ClojureScript code lives under `src/enthalpy`.

## Key Features

- Typing trainer powered by **Reagent** and **re-frame**; global application
  state lives in a single `app-db`.
- Progressive level system (1‑8) with letter sets tuned to Enthium v14
  home‑row frequency.
- QWERTY‑emulation mode (“US KB”) for users typing on a physical US QWERTY
  layout.
- Live cheatsheet keyboard and finger‑colour legend.
- Time/word modes, adjustable limits, and optional caps/punctuation/backspace
  requirements.
- Supplementary documentation pages (rationale, layout, comparison, FAQ) with
  hash‑based routing.
- Build & dev workflow driven by `just` / Shadow‑CLJS, using Bun as the JS
  runtime.

### Development

1. `just install` – install Bun dependencies.
2. `just dev` – start Shadow‑CLJS with hot‑reload at <http://localhost:3000>.
3. `just build` – produce a production bundle in `docs/js`.
4. `just check` / `just test` – perform syntax checks and run the integrity
   test suite (`clj -M scripts/test.clj`).


## License
The ClojureScript code in this repository is licensed under the
**[GNU Affero General Public License v3.0 (AGPL‑3.0)](https://www.gnu.org/licenses/agpl-3.0.en.html)**,
matching the original ColemakCamp project; see `LICENSE` for full text.

Parts of the project that are not a part of this license are mentioned in this table:
| Project Name                                                                            | Project License   |
|-----------------------------------------------------------------------------------------|-------------------|
| [Popper](https://popper.js.org/)                                                        | MIT               |
| [Tippy.js](https://atomiks.github.io/tippyjs/)                                          | MIT               |
| [Fork Awesome CSS](https://github.com/ForkAwesome/Fork-Awesome)                         | MIT               |
| [Cuprum (Google Fonts)](https://fonts.google.com/specimen/Cuprum)                       | Open Font License |
| [Radio Canada (Google Fonts)](https://fonts.google.com/specimen/Radio+Canada)           | Open Font License |
| [Cascadia Code (Microsoft)](https://github.com/microsoft/cascadia-code)                 | Open Font License |
| [Material Icons (Google Fonts)](https://fonts.google.com/icons?selected=Material+Icons) | Apache-2.0        |

## Icon Attributions
Some of the icons have been taken from Flaticon, which requires attribution. Below is a list of all the icons in the `fontasm` font file that are directly taken from Flaticon or modified based on something from Flaticon.
| Icon Class      | Attribution                                                                        |
|-----------------|------------------------------------------------------------------------------------|
| fa-arrow-top    | [Flaticon](https://www.flaticon.com/free-icon/arrow-angle-pointing-to-right_54900) |
| fa-arrow-right  | [Flaticon](https://www.flaticon.com/free-icon/arrow-angle-pointing-to-right_54900) |
| fa-arrow-bottom | [Flaticon](https://www.flaticon.com/free-icon/arrow-angle-pointing-to-right_54900) |
| fa-arrow-left   | [Flaticon](https://www.flaticon.com/free-icon/arrow-angle-pointing-to-right_54900) |
| fa-editor       | [Flaticon](https://www.flaticon.com/free-icon/edit_10074336)                       |
| fa-settings     | [Flaticon](https://www.flaticon.com/free-icon/setting_12439246)                    |
| fa-theme        | [Flaticon](https://www.flaticon.com/free-icon/paint-palette_1763061)               |
| fa-preferences  | [Flaticon](https://www.flaticon.com/free-icon/equalizer_4724337)                   |
| fa-send         | [Flaticon](https://www.flaticon.com/free-icon/message_10426368)                    |
| fa-edit         | [Flaticon](https://www.flaticon.com/free-icon/edit_10074336)                       |
| fa-keyboard     | [Flaticon](https://www.flaticon.com/free-icon/keyboard_4122844)                    |

Icons not listed here are either from Material Icons, or created from scratch.
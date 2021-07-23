import makeStyles from "@material-ui/core/styles/makeStyles";
import { ThemeProps } from "../styles/theme";

export const workspaceStyles = makeStyles({
    filler: {
        flex: 1,
    },

    root: {
        height: "100vh",
        width: "100vw",
        display: "flex",
        flexDirection: "column",
    },

    appBar: {
        height: 48,
        borderBottom: (props: ThemeProps) => `1px solid ${props.theme.panelSeparatorColor}`,
        display: "flex",
        alignItems: "center",
        padding: "0 16px",

        "& > * + *": {
            marginLeft: 8,
        },
    },

    querySplitPane: {
        position: "relative",
        height: "calc(100vh - 48px)",
    },

    editorPane: {
        height: "100%",
    },

    editorTabs: {
        width: "100%",
        height: "calc(100% - 48px)", // TODO: replace these annoying calcs with flexbox where possible
        borderBottom: (props: ThemeProps) => `1px solid ${props.theme.panelSeparatorColor}`,
    },

    editorTabGroup: {
        height: 30,
    },

    editorTab: {
        flex: "0 !important",
        padding: "2px 8px 4px",
        fontSize: 14,
        lineHeight: "20px",
        height: 30,
    },

    editorTabPanel: {
        height: "calc(100% - 30px)",
    },

    actionsBar: {
        height: 48,
        display: "flex",
        alignItems: "center",
        padding: "0 24px",

        "& > * + *": {
            marginLeft: 8,
        },
    },

    resultsPane: {
        height: "100%",
    },

    resultsTabs: {
        height: "calc(100% - 28px)",
    },

    resultsTabGroup: {
        height: 34,
    },

    resultsTab: {
        height: 34,
    },

    resultsTabPanel: {
        height: "calc(100% - 34px)",
        background: (props: ThemeProps) => props.theme.visualiser.colors.hex.background,
    },

    visualiser: {
        height: "100%",
        width: "100%",
    },

    statusBar: {
        borderTop: (props: ThemeProps) => `1px solid ${props.theme.panelSeparatorColor}`,
        height: 28,
        display: "flex",
        alignItems: "center",
        padding: "0 8px",
        fontSize: 14,
        userSelect: "none",
    },
});

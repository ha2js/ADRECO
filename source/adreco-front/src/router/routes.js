import DashboardLayout from "@/layout/dashboard/DashboardLayout.vue";
// GeneralViews
import NotFound from "@/pages/NotFoundPage.vue";

// Admin pages
const Dashboard = () =>
    import ( /* webpackChunkName: "dashboard" */ "@/pages/Dashboard.vue");
const Profile = () =>
    import ( /* webpackChunkName: "common" */ "@/pages/Profile.vue");
const Notifications = () =>
    import ( /* webpackChunkName: "common" */ "@/pages/Notifications.vue");
const Icons = () =>
    import ( /* webpackChunkName: "common" */ "@/pages/Icons.vue");
const Maps = () =>
    import ( /* webpackChunkName: "common" */ "@/pages/Maps.vue");
const Typography = () =>
    import ( /* webpackChunkName: "common" */ "@/pages/Typography.vue");
const TableList = () =>
    import ( /* webpackChunkName: "common" */ "@/pages/TableList.vue");
const AdView = () =>
    import( /* webpackChunkName: "AdView" */ "@/pages/AdView.vue");
const Feedback2 = () =>
    import( /* webpackChunkName: "AdView" */ "@/pages/Feedback2.vue");
const Feedback3 = () =>
    import( /* webpackChunkName: "AdView" */ "@/pages/Feedback3.vue");

const routes = [{
        path: "/",
        component: DashboardLayout,
        redirect: "/dashboard",
        children: [{
                path: "dashboard",
                name: "dashboard",
                component: Dashboard
            },
            {
                path: "profile",
                name: "profile",
                component: Profile
            },
            {
                path: "notifications",
                name: "notifications",
                component: Notifications
            },
            {
                path: "icons",
                name: "icons",
                component: Icons
            },
            {
                path: "maps",
                name: "maps",
                component: Maps
            },
            {
                path: "typography",
                name: "typography",
                component: Typography
            },
            {
                path: "table-list",
                name: "table-list",
                component: TableList
            },
            {
                path: "Feedback2",
                name: "광고별 시청률",
                component: Feedback2
            },
            {
                path: "Feedback3",
                name: "평균 시청 시간",
                component: Feedback3
            }
        ]
    },
    {
        path: "/ad",
        name: "AdView",
        component: AdView
    },
    { path: "*", component: NotFound },
];

/**
 * Asynchronously load view (Webpack Lazy loading compatible)
 * The specified component must be inside the Views folder
 * @param  {string} name  the filename (basename) of the view to load.
function view(name) {
   var res= require('../components/Dashboard/Views/' + name + '.vue');
   return res;
};**/

export default routes;
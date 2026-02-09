import instance from './request'

export const GetTopEchartsList =  (token) => {
    return instance({
        url: '/v2/chart/getEchartsList',
        method: 'GET',
        changOrigin: true,
        headers: {
            "Authorization": token
        }
    })
}

// 获取点击用户数
export const GetClickUsers = (token) => {
    return instance({
        url: '/v2/chart/getClickUsers',
        method: 'GET',
        changOrigin: true,
        headers: {
            "Authorization": token
        }
    })
}

// 获取直接购买用户数
export const GetDirectBuyUsers = (token) => {
    return instance({
        url: '/v2/chart/getDirectBuyUsers',
        method: 'GET',
        changOrigin: true,
        headers: {
            "Authorization": token
        }
    })
}

// 获取收藏后购买用户数
export const GetFavBuyUsers = (token) => {
    return instance({
        url: '/v2/chart/getFavBuyUsers',
        method: 'GET',
        changOrigin: true,
        headers: {
            "Authorization": token
        }
    })
}

// 获取加购后购买用户数
export const GetCartBuyUsers = (token) => {
    return instance({
        url: '/v2/chart/getCartBuyUsers',
        method: 'GET',
        changOrigin: true,
        headers: {
            "Authorization": token
        }
    })
}
// 获取收藏且加购后购买用户数
export const GetBothBuyUsers = (token) => {
    return instance({
        url: '/v2/chart/getBothBuyUsers',
        method: 'GET',
        changOrigin: true,
        headers: {
            "Authorization": token
        }
    })
}

//获取用户行为小时分布数据
export const GetUserBehaviorHour = (token) => {
    return instance({
        url: '/v2/chart/getUserBehaviorHour',
        method: 'GET',
        changOrigin: true,
        headers: {
            "Authorization": token
        }
    })
}



export const GetAllChartList = (token) => {
    return instance({
        url: '/v2/chart/getAllChartList',
        method: 'GET',
        changOrigin: true,
        headers: {
            "Authorization": token
        }
    })
}

export const GetPublicChartList = (token) => {
    return instance({
        url: '/v2/chart/getPublicChartList',
        method: 'GET',
        changOrigin: true,
        headers: {
            "Authorization": token
        }
    })
}

export const GetDepChartList = (token) => {
    return instance({
        url: '/v2/chart/getDepChartList',
        method: 'GET',
        changOrigin: true,
        headers: {
            "Authorization": token
        }
    })
}

export const GetSelfChartList = (token) => {
    return instance({
        url: '/v2/chart/getSelfChartList',
        method: 'GET',
        changOrigin: true,
        headers: {
            "Authorization": token
        }
    })
}

export const GetRecChartList = (token) => {
    return instance({
        url: '/v2/chart/getRecChartList',
        method: 'GET',
        changOrigin: true,
        headers: {
            "Authorization": token
        }
    })
}

export const SaveChartInfo = (res, token) => {
    return instance({
        url: '/v2/chart/saveChartInfo',
        method: 'POST',
        changOrigin: true,
        data: res,
        headers: {
            "Authorization": token
        }
    })
}

export const DeleteChart = (res, token) => {
    return instance({
        url: '/v2/chart/deleteChart',
        method: 'POST',
        changOrigin: true,
        data: res,
        headers: {
            "Authorization": token
        }
    })
}

export const GetChartAdviceList = (res, token) => {
    return instance({
        url: '/v2/chart/getChartAdviceList',
        method: 'POST',
        changOrigin: true,
        data: res,
        headers: {
            "Authorization": token
        }
    })
}

export const SaveChartAdvice = (res, token) => {
    return instance({
        url: '/v2/chart/saveChartAdvice',
        method: 'POST',
        changOrigin: true,
        data: res,
        headers: {
            "Authorization": token
        }
    })
}

export const DeleteChartAdvice = (res, token) => {
    return instance({
        url: '/v2/chart/deleteChartAdvice',
        method: 'POST',
        changOrigin: true,
        data: res,
        headers: {
            "Authorization": token
        }
    })
}

export default {
    GetTopEchartsList,
    GetAllChartList, GetPublicChartList, GetDepChartList, GetSelfChartList,
    GetRecChartList, SaveChartInfo, DeleteChart, GetChartAdviceList, SaveChartAdvice, DeleteChartAdvice
}
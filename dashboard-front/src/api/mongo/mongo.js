import request from '@/utils/request'

// var path = window.location.pathname;
// if (path.length > 0 && path.charAt(path.length-1) === '/') {
//   path = path.substring(0, path.length-1)
// }
export function excuteCommand(data) {
  return request({
    url: '/mongo/command/execute',
    method: 'post',
    data
  })
}

export function getCollections() {
  return request({
    url: '/mongo/getCollectionList',
    method: 'get',
  })
}

export function getEntityNumBySkill(skillId, skillVersion) {
  return request({
    url: '/mongo/getEntityNumBySkill',
    method: 'get',
    params: {
      'skillId': skillId,
      'skillVersion': skillVersion
    }
  })
}

export function getAttributesByName() {
  return request({
    url: '/mongo/getAttributesByName',
    method: 'get'
  })
}

export function getCurrentKnowledgeVersion() {
  return request({
    url: '/mongo/getCurrentKnowledgeVersion',
    method: 'get'
  })
}

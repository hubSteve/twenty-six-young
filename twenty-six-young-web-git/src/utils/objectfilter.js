export function filterObj (obj) {
  if (!(typeof obj === 'object')) {
    return
  }

  for (var key in obj) {
    if (obj.hasOwnProperty(key) && (obj[key] == null || obj[key] === undefined || obj[key] === '')) {
      delete obj[key]
    }
  }
  return obj
}
